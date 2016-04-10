/**
 * @author mrdoob / http://mrdoob.com/
 * @author alteredq / http://alteredqualia.com/
 * @author paulirish / http://paulirish.com/
 */

THREE.FirstPersonControls = function (object, dragCamera, domElement) {

  //  static

  this.camera = object;
  this.dragCamera = dragCamera;
  this.target = new THREE.Vector3(1, 0, 0);
  this.tmpVector = new THREE.Vector3(0, 0, 0);
  this.UP = new THREE.Vector3(0, 0, 1);

  this.domElement = ( domElement !== undefined ) ? domElement : document;


  this.MOVEMENT_SPEED = 15;

  //  dynamic

  this.mouseX = 0;
  this.mouseY = 0;

  this.lastUpdateMouseX = 0;
  this.lastUpdateMouseY = 0;

  this.origLookPhi = 0;
  this.origLookTheta = 0;

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

  this.getProjectedDir = function (mouseX, mouseY, camera) {

    var adjX = ( mouseX / window.innerWidth ) * 2 - 1;
    var adjY = -( mouseY / window.innerHeight ) * 2 + 1;

    var vector = new THREE.Vector3(adjX, adjY, 0.5);
    return vector.unproject(camera).normalize();
  };

  this.onMouseDown = function (event) {

    if (this.domElement !== document) {
      this.domElement.focus();
    }

    //  hacky?  yeah probably

    if (event.target.tagName == "CANVAS") {

      this.dragView = true;

      this.mouseX = event.pageX;
      this.mouseY = event.pageY;

      //var cameraPos = this.camera.position;
      //this.dragCamera.position.set(
      //    cameraPos.x,
      //    cameraPos.y,
      //    cameraPos.z
      //);

      //this.dragCamera.up = this.UP;
      //this.dragCamera.rotation.x = this.camera.rotation.x;
      //this.dragCamera.rotation.y = this.camera.rotation.y;
      //this.dragCamera.rotation.z = this.camera.rotation.z;
      //
      //this.dragCamera.rotation.set(
      //    this.camera.rotation.x,
      //    this.camera.rotation.y,
      //    this.camera.rotation.z
      //);

      //var origLook = this.dragCamera.getWorldDirection().normalize();
      //console.log(origLook);
      //var currentDir = this.camera.getWorldDirection().normalize();
      //this.origLookPhi = this.toPhi(currentDir.x, currentDir.y, currentDir.z);
      //this.origLookTheta = this.toTheta(currentDir.x, currentDir.y, currentDir.z);
      //
      //var origDir = this.getProjectedDir(this.mouseX, this.mouseY, this.camera);
      //this.lastUpdateTheta = this.toTheta(origDir.x, origDir.y, origDir.z);
      //this.lastUpdatePhi = this.toPhi(origDir.x, origDir.y, origDir.z);

      this.lastUpdateMouseX = this.mouseX;
      this.lastUpdateMouseY = this.mouseY;

      //var cameraLookAt = this.camera.getWorldDirection();

      //this.origLookAt = new THREE.Vector3(
      //    cameraLookAt.x,
      //    cameraLookAt.y,
      //    cameraLookAt.z
      //);
      //
      //this.origPos = new THREE.Vector3(
      //    this.camera.position.x,
      //    this.camera.position.y,
      //    this.camera.position.z
      //);
      //
      //console.log("mouse click on location: ");
      //console.log(this.dragFixPos);

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

    //event.preventDefault();

    switch (event.keyCode) {

      case 38: /*up*/
      case 87: /*W*/
        this.moveForward = true;
        break;

      case 37: /*left*/
      case 65: /*A*/
        this.moveLeft = true;
        break;

      case 40: /*down*/
      case 83: /*S*/
        this.moveBackward = true;
        break;

      case 39: /*right*/
      case 68: /*D*/
        this.moveRight = true;
        break;

      case 82: /*R*/
        this.moveUp = true;
        break;
      case 70: /*F*/
        this.moveDown = true;
        break;


      case 74:
        this.rotateYU = true;
        break;
      case 76:
        this.rotateYD = true;
        break;
      case 73:
        this.rotateXL = true;
        break;
      case 75:
        this.rotateXR = true;
        break;
      case 85:
        this.rotateZL = true;
        break;
      case 79:
        this.rotateZR = true;
        break;
    }

  };

  this.onKeyUp = function (event) {

    switch (event.keyCode) {

      case 38: /*up*/
      case 87: /*W    */
        this.moveForward = false;
        break;

      case 37: /*left*/
      case 65: /*A*/
        this.moveLeft = false;
        break;

      case 40: /*down*/
      case 83: /*S*/
        this.moveBackward = false;
        break;

      case 39: /*right*/
      case 68: /*D*/
        this.moveRight = false;
        break;

      case 82: /*R*/
        this.moveUp = false;
        break;
      case 70: /*F*/
        this.moveDown = false;
        break;

      case 74:
        this.rotateYU = false;
        break;
      case 76:
        this.rotateYD = false;
        break;
      case 73:
        this.rotateXL = false;
        break;
      case 75:
        this.rotateXR = false;
        break;
      case 85:
        this.rotateZL = false;
        break;
      case 79:
        this.rotateZR = false;
        break;

    }

  };

  this.update = function (delta, lockPos) {

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

    //  invert


    if (this.dragView) {

      console.log("asdfsadfdfdasf");

      console.log(this.lastUpdateMouseX);
      console.log(this.mouseX);

      var oldProjected = this.getProjectedDir(this.lastUpdateMouseX, this.lastUpdateMouseY, this.camera);
      var newProjected = this.getProjectedDir(this.mouseX, this.mouseY, this.camera);

      console.log(oldProjected);
      console.log(newProjected);

      var newPhi = this.toPhi(newProjected.x, newProjected.y, newProjected.z);
      var newTheta = this.toTheta(newProjected.x, newProjected.y, newProjected.z);

      var oldPhi = this.toPhi(oldProjected.x, oldProjected.y, oldProjected.z);
      var oldTheta = this.toTheta(oldProjected.x, oldProjected.y, oldProjected.z);

      //console.log(newPhi);
      //console.log(newTheta);
      //
      //console.log(newPhi - oldPhi);oldPhi
      //console.log(newTheta - oldTheta);

      this.camera.rotateY(newPhi - oldPhi);
      this.camera.rotateX(newTheta - oldTheta);
      //
      //console.log();
      //console.log(this.lastUpdateMouseX);
      //console.log(this.lastUpdateMouseY);
      //console.log(this.mouseX);
      //console.log(this.mouseY);

      this.lastUpdateMouseX = this.mouseX;
      this.lastUpdateMouseY = this.mouseY;

      //var currentDir = this.camera.getWorldDirection().normalize();
      //this.lastUpdatePhi = this.toPhi(currentDir.x, currentDir.y, currentDir.z);
      //this.lastUpdateTheta = this.toTheta(currentDir.x, currentDir.y, currentDir.z);

    }

    //this.camera.up = this.UP;
    // this.camera.lookAt(this.target);
    //this.camera.rotation.z = 0;

  };

  this.toPhi = function (x, y, z) {
    return Math.atan(y / x);
  };

  this.toTheta = function (x, y, z) {
    return Math.acos(z / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));
  };

  this.domElement.addEventListener('contextmenu', function (event) {
    event.preventDefault();
  }, false);

  this.domElement.addEventListener('mousemove', bind(this, this.onMouseMove), false);
  this.domElement.addEventListener('mousedown', bind(this, this.onMouseDown), false);
  this.domElement.addEventListener('mousewheel', bind(this, this.onMouseWheel), false);
  this.domElement.addEventListener('DOMMouseScroll', bind(this, this.onMouseWheel), false); // firefox

  this.domElement.addEventListener('mouseup', bind(this, this.onMouseUp), false);
  this.domElement.addEventListener('keydown', bind(this, this.onKeyDown), false);
  this.domElement.addEventListener('keyup', bind(this, this.onKeyUp), false);

  function bind(scope, fn) {

    return function () {

      fn.apply(scope, arguments);

    };

  };

};