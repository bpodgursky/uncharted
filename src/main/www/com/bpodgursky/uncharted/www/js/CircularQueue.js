function CircularQueue(size, initialValue) {

  this.buffer = [];
  for(var i = 0; i < size; i++){
    this.buffer.push(initialValue);
  }
  this.size = size;
  this.pointer = 0;

}

CircularQueue.prototype.put = function(val){

  var ret = this.buffer[this.pointer];
  this.buffer[this.pointer] = val;

  this.pointer += 1;
  if(this.pointer == this.size){
    this.pointer = 0;
  }

  return ret;
};