/**
 * @author mrdoob / http://mrdoob.com/
 * @author alteredq / http://alteredqualia.com/
 * @author paulirish / http://paulirish.com/
 */

THREE.FirstPersonControls = function (object, domElement) {

  //  static

  this.camera = object;
  this.target = new THREE.Vector3(0, 0, 0);
  this.tmpVector = new THREE.Vector3(0, 0, 0);
  this.tmpCamera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 1, 10000);
  this.tmpCamera .aspect = window.innerWidth / window.innerHeight;
  this.tmpCamera.updateProjectionMatrix();


  this.domElement = ( domElement !== undefined ) ? domElement : document;


  this.MOVEMENT_SPEED = 15;

  //  dynamic

  this.mouseX = 0;
  this.mouseY = 0;

  this.dragFixPos;
  this.origLookAt;
  this.origPos;

  this.lat = 0;
  this.lon = 0;
  this.phi = 0;
  this.theta = 0;

  this.moveForward = false;
  this.moveBackward = false;
  this.moveLeft = false;
  this.moveRight = false;

  this.dragView = false;

  if (this.domElement !== document) {

    this.domElement.setAttribute('tabindex', -1);

  }

  this.getProjectedPos = function (mouseX, mouseY, camera) {

    var adjX = ( mouseX / window.innerWidth ) * 2 - 1;
    var adjY = -( mouseY / window.innerHeight ) * 2 + 1;

    this.tmpVector.set(adjX, adjY, 0.5);

    console.log("asdfafadsfafads");
    console.log(this.tmpVector);

    this.tmpVector.unproject(camera);

    console.log(this.tmpVector);

    var dir = this.tmpVector.sub(camera.position).normalize();
    var distance = -camera.position.z / dir.z;

    console.log(dir);
    console.log(distance);

    return camera.position.clone().add(dir.multiplyScalar(distance));

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

      this.dragFixPos = this.getProjectedPos(this.mouseX, this.mouseY, this.camera);

      var cameraLookAt = this.camera.getWorldDirection();

      this.origLookAt = new THREE.Vector3(
        cameraLookAt.x,
        cameraLookAt.y,
        cameraLookAt.z
      );

      this.origPos = new THREE.Vector3(
        this.camera.position.x,
        this.camera.position.y,
        this.camera.position.z
      );

      console.log("mouse click on location: ");
      console.log(this.dragFixPos);

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

    //  invert


    if (this.dragView) {

      console.log("WAAAAAAAA");
      console.log(this.origPos);
      console.log(this.origLookAt);

      //  TODO only set in orig drag
      this.tmpCamera.position.set(this.origPos);
      this.tmpCamera.lookAt(this.origLookAt);

      var newProjected = this.getProjectedPos(this.mouseX, this.mouseY, this.tmpCamera);

      console.log(newProjected);

      var deltaX = newProjected.x - this.dragFixPos.x;
      var deltaY = newProjected.y - this.dragFixPos.y;
      var deltaZ = newProjected.z - this.dragFixPos.z;

      this.target.x = this.origLookAt.x + deltaX;
      this.target.y = this.origLookAt.y + deltaY;
      this.target.z = this.origLookAt.z + deltaZ;

    }

    this.camera.lookAt(this.target);

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