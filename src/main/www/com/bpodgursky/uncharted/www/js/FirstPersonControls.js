/**
 * @author mrdoob / http://mrdoob.com/
 * @author alteredq / http://alteredqualia.com/
 * @author paulirish / http://paulirish.com/
 */

THREE.FirstPersonControls = function (object, domElement) {

  //  static

  this.object = object;
  this.target = new THREE.Vector3(0, 0, 0);

  this.domElement = ( domElement !== undefined ) ? domElement : document;

  this.MOVEMENT_SPEED = 15;

  //  dynamic

  this.mouseX = 0;
  this.mouseY = 0;

  //this.dragStartX = 0;
  //this.dragStartY = 0;
  //
  //this.dragStartLat = 0;
  //this.dragStartLon = 0;

  this.dragStartPhi = 0;
  this.dragStarTheta = 0;

  this.originalPhi = 0;
  this.originalTheta = 0;

  this.lat = 0;
  this.lon = 0;
  this.phi = 0;
  this.theta = 0;

  this.moveForward = false;
  this.moveBackward = false;
  this.moveLeft = false;
  this.moveRight = false;

  this.viewHalfX = 0;
  this.viewHalfY = 0;

  this.dragView = false;

  if (this.domElement !== document) {

    this.domElement.setAttribute('tabindex', -1);

  }

  this.handleResize = function () {

    if (this.domElement === document) {

      this.viewHalfX = window.innerWidth / 2;
      this.viewHalfY = window.innerHeight / 2;

    } else {

      this.viewHalfX = this.domElement.offsetWidth / 2;
      this.viewHalfY = this.domElement.offsetHeight / 2;

    }

  };

  this.onMouseDown = function (event) {

    if (this.domElement !== document) {
      this.domElement.focus();
    }

    //  hacky?  yeah probably

    if (event.target.tagName == "CANVAS") {

      this.dragView = true;
      //this.dragStartX = event.pageX - this.viewHalfX;
      //this.dragStartY = event.pageY - this.viewHalfY;
      //
      //this.dragStartLat = this.lat;
      //this.dragStartLon = this.lon;

      this.dragStartPhi = THREE.Math.degToRad(90 - event.pageY + this.viewHalfY);
      this.dragStarTheta = THREE.Math.degToRad(event.pageX - this.viewHalfX);

      this.originalPhi = this.phi;
      this.originalTheta = this.theta;

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
      if (this.domElement === document) {

        this.mouseX = event.pageX - this.viewHalfX;
        this.mouseY = event.pageY - this.viewHalfY;

      } else {

        this.mouseX = event.pageX - this.domElement.offsetLeft - this.viewHalfX;
        this.mouseY = event.pageY - this.domElement.offsetTop - this.viewHalfY;

      }
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
      this.object.translateZ(-( actualMoveSpeed ));
    }

    if (this.moveBackward) {
      this.object.translateZ(actualMoveSpeed);
    }

    this.moveForward = false;
    this.moveBackward = false;

    if (this.moveLeft) {
      this.object.translateX(-actualMoveSpeed);
    }

    if (this.moveRight) {
      this.object.translateX(actualMoveSpeed);
    }

    if (this.moveUp) {
      this.object.translateY(actualMoveSpeed);
    }

    if (this.moveDown) {
      this.object.translateY(-actualMoveSpeed);
    }

    //  invert

    //if(this.dragView) {
    //  this.lon = this.mouseX;
    //  this.lat = this.mouseY;
    //}

    this.lat = Math.max(-85, Math.min(85, this.lat));

    var mousePhi = THREE.Math.degToRad(90 - this.mouseY);
    var mouseTheta = THREE.Math.degToRad(this.mouseX);

    var deltaPhi = this.dragStartPhi - mousePhi;
    var deltaTheta = this.dragStarTheta - mouseTheta;

    this.phi = this.originalPhi - deltaPhi;
    this.theta = this.originalTheta + deltaTheta;

    var position = this.object.position;

    this.target.x = position.x + 100 * Math.sin(this.phi) * Math.cos(this.theta);
    this.target.y = position.y + 100 * Math.cos(this.phi);
    this.target.z = position.z + 100 * Math.sin(this.phi) * Math.sin(this.theta);

    //  look at drag start point offset from center

    this.object.lookAt(this.target);

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

  this.handleResize();

};