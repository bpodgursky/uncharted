THREE.TweenControls = function (object) {

  this.camera = object;
  this.targetX = null;
  this.targetY = null;
  this.targetZ = null;

  this.startX = null;
  this.startY = null;
  this.startZ = null;

  this.tMax = null;
  this.tCurrent = null;

  this.xMid = null;
  this.yMid = null;
  this.zMid = null;

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

  this.lookAt = function (rotation, onComplete, time) {

    this.startX = this.camera.rotation.x;
    this.startY = this.camera.rotation.y;
    this.startZ = this.camera.rotation.z;

    this.targetX = this.getTarget(this.startX, rotation.x);
    this.targetY = this.getTarget(this.startY, rotation.y);
    this.targetZ = this.getTarget(this.startZ, rotation.z);

    this.tMax = time;
    this.tCurrent = 0;
    this.xMid = (this.camera.rotation.x + this.targetX) / 2;
    this.yMid = (this.camera.rotation.y + this.targetY) / 2;
    this.zMid = (this.camera.rotation.z + this.targetZ) / 2;

    this.onComplete = onComplete;
  };

  this.updateVar = function (mid, target, start) {
    return mid + (target - start) / 2 * Math.sin(Math.PI * this.tCurrent / this.tMax - Math.PI / 2)
  };

  this.update = function (delta) {

    this.camera.rotation.x = this.updateVar(this.xMid, this.targetX, this.startX);
    this.camera.rotation.y = this.updateVar(this.yMid, this.targetY, this.startY);
    this.camera.rotation.z = this.updateVar(this.zMid, this.targetZ, this.startZ);

    this.tCurrent += delta;

    if (this.tCurrent >= this.tMax) {
      this.onComplete();
    }

  }

};