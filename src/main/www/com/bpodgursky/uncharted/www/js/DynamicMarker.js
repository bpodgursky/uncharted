function DynamicMarker(position, markRadius, color, initialZ) {

  var shape1 = new THREE.Shape();
  shape1.moveTo(0, 0);
  shape1.lineTo(.025, .10);
  shape1.lineTo(-.025, .10);
  shape1.lineTo(0, 0);
  this.tip1 = this.cornerMesh(shape1, color);
  this.tip1.position.y = markRadius;

  var shape2 = new THREE.Shape();
  shape2.moveTo(0, 0);
  shape2.lineTo(-.025, -.10);
  shape2.lineTo(.025, -.10);
  shape2.lineTo(0, 0);
  this.tip2 = this.cornerMesh(shape2, color);
  this.tip2.position.y = -markRadius;

  var shape3 = new THREE.Shape();
  shape3.moveTo(0, 0);
  shape3.lineTo(.1, -.025);
  shape3.lineTo(.1, .025);
  shape3.lineTo(0, 0);
  this.tip3 = this.cornerMesh(shape3, color);
  this.tip3.position.x = markRadius;

  var shape4 = new THREE.Shape();
  shape4.moveTo(0, 0);
  shape4.lineTo(-.1, -.025);
  shape4.lineTo(-.1, .025);
  shape4.lineTo(0, 0);
  this.tip4 = this.cornerMesh(shape4, color);
  this.tip4.position.x = -markRadius;

  this.mesh = new THREE.Group();
  this.mesh.add(this.tip1);
  this.mesh.add(this.tip2);
  this.mesh.add(this.tip3);
  this.mesh.add(this.tip4);
  this.mesh.position.x = position.x;
  this.mesh.position.y = position.y;
  this.mesh.position.z = position.z;

  this.mesh.rotation.z = initialZ;
}

DynamicMarker.prototype.target = function(position){
  this.mesh.position.x = position.x;
  this.mesh.position.y = position.y;
  this.mesh.position.z = position.z
};

DynamicMarker.prototype.cornerMesh = function(shape, color) {
  var geom = new THREE.ShapeGeometry(shape);
  return new THREE.Mesh(geom, new THREE.MeshBasicMaterial({
    color: color,
    side: THREE.DoubleSide,
    wireframe: false
  }));
};

DynamicMarker.prototype.updateCorner = function(corner, scale){
  corner.scale.x = scale;
  corner.scale.y = scale;
  corner.scale.z = scale;
};

DynamicMarker.prototype.updateTo = function (camera, delta) {

  var scale = this.mesh.position.distanceTo(camera.position) * .2;
  this.updateCorner(this.tip1, scale);
  this.updateCorner(this.tip2, scale);
  this.updateCorner(this.tip3, scale);
  this.updateCorner(this.tip4, scale);

  this.mesh.rotation.x = camera.rotation.x;
  this.mesh.rotation.y = camera.rotation.y;
  this.mesh.rotateZ(.5 * delta);

};