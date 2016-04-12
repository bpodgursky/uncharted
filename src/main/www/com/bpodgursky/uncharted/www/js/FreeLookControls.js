/**
 * @author mrdoob / http://mrdoob.com/
 * @author alteredq / http://alteredqualia.com/
 * @author paulirish / http://paulirish.com/
 */

THREE.FreeLookControls = function (object, domElement) {

  //  static

  this.camera = object;
  this.domElement = domElement;

  this.MOVEMENT_SPEED = 30;

  //  dynamic

  this.mouseX = 0;
  this.mouseY = 0;

  this.lastUpdateMouseX = 0;
  this.lastUpdateMouseY = 0;

  this.moveForward = false;
  this.moveBackward = false;
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
      delta = event.wheelDelta / 40;
    } else if (event.detail) { // Firefox
      delta = -event.detail / 3;
    }

    if (delta > 0) {
      this.moveForward = true;
    } else if (delta < 0) {
      this.moveBackward = true;
    }

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
        this.moveForward = trigger;
        break;
      case 37: /*left*/
      case 65: /*A*/
        this.moveLeft = trigger;
        break;
      case 40: /*down*/
      case 83: /*S*/
        this.moveBackward = trigger;
        break;
      case 39: /*right*/
      case 68: /*D*/
        this.moveRight = trigger;
        break;

      case 82: /*R*/
        this.moveUp = trigger;
        break;
      case 70: /*F*/
        this.moveDown = trigger;
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

    var actualMoveSpeed = delta * this.MOVEMENT_SPEED;

    if (this.moveForward) {
      this.camera.translateZ(-( actualMoveSpeed ));
    }

    if (this.moveBackward) {
      this.camera.translateZ(actualMoveSpeed);
    }

    this.moveForward = false;
    this.moveBackward = false;

    if (this.moveLeft) {
      this.camera.translateX(-actualMoveSpeed);
    }

    if (this.moveRight) {
      this.camera.translateX(actualMoveSpeed);
    }

    if (this.moveUp) {
      this.camera.translateY(actualMoveSpeed);
    }

    if (this.moveDown) {
      this.camera.translateY(-actualMoveSpeed);
    }

    if (this.rotateXL) {
      this.camera.rotateX(.01);
    }
    if (this.rotateXR) {
      this.camera.rotateX(-.01)
    }
    if (this.rotateYU) {
      this.camera.rotateY(.01);
    }
    if (this.rotateYD) {
      this.camera.rotateY(-.01);
    }
    if (this.rotateZL) {
      this.camera.rotateZ(.01);
    }
    if (this.rotateZR) {
      this.camera.rotateZ(-.01);
    }

    if (this.dragView) {

      var diffX = (this.mouseX - this.lastUpdateMouseX) / window.innerWidth;
      var diffY = (this.mouseY - this.lastUpdateMouseY) / window.innerHeight;

      this.camera.rotateY(.8 * diffX);
      this.camera.rotateX(.8 * diffY);

      this.lastUpdateMouseX = this.mouseX;
      this.lastUpdateMouseY = this.mouseY;

    }
  };

};