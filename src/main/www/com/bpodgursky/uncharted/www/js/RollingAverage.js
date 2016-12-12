function RollingAverage(size, initialAvg){

  this.queue = new CircularQueue(size, initialAvg);
  this.sum = size * initialAvg;
  this.size = size;

}

RollingAverage.prototype.add = function(val){

  var popped = this.queue.put(val);

  this.sum += val;
  this.sum -= popped;

  return this.sum / this.size;

};