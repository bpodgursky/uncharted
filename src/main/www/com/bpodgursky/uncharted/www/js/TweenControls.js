THREE.TweenControls = function (object) {

  this.camera = object;
  this.target = null;
  this.radius = null;

  this.targetRotation = null;
  this.startRotation = null;
  this.midRotation = null;

  this.targetPosition = null;
  this.startPosition = null;
  this.midPosition = null;

  this.tMax = null;
  this.tCurrent = null;

  this.onComplete = null;

  this.getTarget = function (start, target) {

    var raw = target - start;
    if (raw > Math.PI) {
      return target - 2 * Math.PI
    }

    if (raw < -Math.PI) {
      return target + 2 * Math.PI;
    }

    return target;

  };
  
  
  this.recomputeTargets = function(){

    var startRotation = new THREE.Euler().copy(camera.rotation);
    var startPosition = new THREE.Vector3().copy(camera.position);

    camera.lookAt(this.target);

    var distance = camera.position.distanceTo(this.target);
    camera.translateZ(-distance + this.radius);

    var endRotation = new THREE.Euler().copy(camera.rotation);
    var endPosition = new THREE.Vector3().copy(camera.position);

    camera.rotation.copy(startRotation);
    camera.position.copy(startPosition);

    this.targetPosition = endPosition;

    this.targetRotation = {
      x: this.getTarget(this.startRotation.x, endRotation.x),
      y: this.getTarget(this.startRotation.y, endRotation.y),
      z: this.getTarget(this.startRotation.z, endRotation.z)
    };

    this.midRotation = {
      x: (this.startRotation.x + this.targetRotation.x) / 2,
      y: (this.startRotation.y + this.targetRotation.y) / 2,
      z: (this.startRotation.z + this.targetRotation.z) / 2
    };

    this.midPosition = {
      x: (this.startPosition.x + this.targetPosition.x) / 2,
      y: (this.startPosition.y + this.targetPosition.y) / 2,
      z: (this.startPosition.z + this.targetPosition.z) / 2
    };

  }

  this.setZoom = function (target, radius, time, onComplete) {

    this.target = target;
    this.radius = radius;

    this.startRotation = {
      x: this.camera.rotation.x,
      y: this.camera.rotation.y,
      z: this.camera.rotation.z
    };

    this.startPosition = {
      x: this.camera.position.x,
      y: this.camera.position.y,
      z: this.camera.position.z
    };

    this.recomputeTargets();

    this.tMax = time;
    this.tCurrent = 0;

    this.onComplete = onComplete;
  };

  this.updateVar = function (mid, target, start) {
    return mid + (target - start) / 2 * Math.sin(Math.PI * this.tCurrent / this.tMax - Math.PI / 2)
  };

  this.updateVec = function (mid, start, target, update) {
    update.x = this.updateVar(mid.x, target.x, start.x);
    update.y = this.updateVar(mid.y, target.y, start.y);
    update.z = this.updateVar(mid.z, target.z, start.z);
  };

  this.update = function (delta) {

    this.updateVec(this.midRotation, this.startRotation, this.targetRotation, this.camera.rotation);
    this.updateVec(this.midPosition, this.startPosition, this.targetPosition, this.camera.position);

    this.tCurrent += delta;

    if (this.tCurrent >= this.tMax) {
      this.onComplete();
    }

  }

};