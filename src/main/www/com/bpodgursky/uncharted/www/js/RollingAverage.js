function RollingAverage(size, initialAvg, ignoreBelowFactor) {

  this.queue = new CircularQueue(size, initialAvg);
  this.sum = size * initialAvg;
  this.size = size;
  this.ignoreBelowFactor = ignoreBelowFactor;
}

RollingAverage.prototype.add = function (val) {

  if (val > this.ignoreBelowFactor * val) {

    var popped = this.queue.put(val);

    this.sum += val;
    this.sum -= popped;

  }

  return this.sum / this.size;

};