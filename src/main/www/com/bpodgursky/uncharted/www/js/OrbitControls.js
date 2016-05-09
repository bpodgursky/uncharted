THREE.OrbitControls = function (object, domElement, target, sceneParent) {

  //  static

  this.scene = sceneParent;
  this.camera = object;
  this.target = target;
  this.domElement = domElement;

  this.MOVEMENT_SPEED = 30;

  this.UP = new THREE.Vector3(0, 0, 1);

  //  dynamic

  this.mouseX = 0;
  this.mouseY = 0;

  this.lastUpdateMouseX = 0;
  this.lastUpdateMouseY = 0;

  this.moveForward = false;
  this.moveBackward = false;

  this.moveDelta = 0.0;

  this.moveLeft = false;
  this.moveRight = false;
  this.rotateXR = false;
  this.rotateXL = false;
  this.rotateYU = false;
  this.rotateYD = false;
  this.rotateZL = false;
  this.rotateZR = false;

  this.dragView = false;

  if (this.domElement !== document) {
    this.domElement.setAttribute('tabindex', -1);
  }

  this.onMouseDown = function (event) {

    if (this.domElement !== document) {
      this.domElement.focus();
    }

    //  hacky?  yeah probably

    if (event.target.tagName == "CANVAS") {

      this.dragView = true;

      this.mouseX = event.pageX;
      this.mouseY = event.pageY;

      this.lastUpdateMouseX = this.mouseX;
      this.lastUpdateMouseY = this.mouseY;

      event.preventDefault();
      event.stopPropagation();
    }

  };

  this.onMouseUp = function (event) {

    event.preventDefault();
    event.stopPropagation();

    this.dragView = false;

  };

  this.onMouseMove = function (event) {

    if (this.dragView) {
      this.mouseX = event.pageX;
      this.mouseY = event.pageY;
    }
  };

  this.onMouseWheel = function (event) {

    event.preventDefault();
    event.stopPropagation();

    var delta = 0;

    if (event.wheelDelta) { // WebKit / Opera / Explorer 9
      delta = - event.wheelDelta / 2000;
    } else if (event.detail) { // Firefox
      delta = -event.detail / 300;
    }

    this.moveDelta += delta;

  };

  this.onKeyDown = function (event) {
    this.onKey(event, true);
  };

  this.onKeyUp = function (event) {
    this.onKey(event, false);
  };

  this.onKey = function (event, trigger) {

    switch (event.keyCode) {

      case 38: /*up*/
      case 87: /*W    */
          if(trigger) {
            this.moveDelta = -.1;
          }
        break;
      case 40: /*down*/
      case 83: /*S*/
          if(trigger){
            this.moveDelta = .1;
          }
        break;

      case 74:
        this.rotateYU = trigger;
        break;
      case 76:
        this.rotateYD = trigger;
        break;
      case 73:
        this.rotateXL = trigger;
        break;
      case 75:
        this.rotateXR = trigger;
        break;
      case 85:
        this.rotateZL = trigger;
        break;
      case 79:
        this.rotateZR = trigger;
        break;

    }

  };

  this.update = function (delta) {

    var dO = this.target.distanceTo(this.scene.position);
    var actualMoveSpeed = this.moveDelta * dO;

    //  don't zoom forwards if we're too close (or you'll zoom past it
    if (this.moveDelta != 0) {

      if (dO + actualMoveSpeed < .08) {
        var advance = dO - .08;
        this.scene.translateZ(- advance);
      } else {
        this.scene.translateZ(actualMoveSpeed);
      }
    }

    this.moveDelta = 0;

    //  reset z
    dO = this.target.distanceTo(this.camera.position);

    this.moveForward = false;
    this.moveBackward = false;

    //  TODO make this work
    if (this.rotateZL) {
      this.scene.rotateZ(.02);
    }
    if (this.rotateZR) {
      this.scene.rotateZ(-.02);
    }

    this.rotateZL = false;
    this.rotateZR = false;

    var translateX = 0;
    var translateY = 0;

    if (this.dragView) {

      var diffX = (this.mouseX - this.lastUpdateMouseX) / window.innerWidth;
      var diffY = -(this.mouseY - this.lastUpdateMouseY) / window.innerHeight;

      translateX -= 10 * diffX;
      translateY -= 10 * diffY;

      this.lastUpdateMouseX = this.mouseX;
      this.lastUpdateMouseY = this.mouseY;

    }

    translateX += .04 * dO  * delta;

    this.scene.translateX(translateX);
    this.scene.rotateY(Math.atan(translateX / dO));

    this.scene.translateY(translateY);
    this.scene.rotateX(-Math.atan(translateY / dO));

    var dN = this.target.distanceTo(this.camera.position);

    this.scene.translateZ(-(dN - dO));

  };


};