// function HighlightMarker(position, markRadius, color) {
//
//   var shape1 = new THREE.Shape();
//   shape1.moveTo(0, 0);
//   shape1.lineTo(.025, .10);
//   shape1.lineTo(-.025, .10);
//   shape1.lineTo(0, 0);
//   this.tip1 = this.cornerMesh(shape1, color);
//   this.tip1.position.y = markRadius;
//
//   this.mesh = new THREE.Group();
//   this.mesh.add(this.tip1);
//   this.mesh.position.x = position.x;
//   this.mesh.position.y = position.y;
//   this.mesh.position.z = position.z;
//
//   this.mesh.rotation.z = 0;
// }
//
// DynamicMarker.prototype.target = function(position){
//   this.mesh.position.x = position.x;
//   this.mesh.position.y = position.y;
//   this.mesh.position.z = position.z
// };
//
// DynamicMarker.prototype.cornerMesh = function(shape, color) {
//   var geom = new THREE.ShapeGeometry(shape);
//   return new THREE.Mesh(geom, new THREE.MeshBasicMaterial({
//     color: color,
//     side: THREE.DoubleSide,
//     wireframe: false
//   }));
// };
//
// DynamicMarker.prototype.updateCorner = function(corner, scale){
//   corner.scale.x = scale;
//   corner.scale.y = scale;
//   corner.scale.z = scale;
// };
//
// DynamicMarker.prototype.updateTo = function (camera, delta) {
//
//   var scale = this.mesh.position.distanceTo(camera.position) * .2;
//   this.updateCorner(this.tip1, scale);
//
//   this.mesh.rotation.x = camera.rotation.x;
//   this.mesh.rotation.y = camera.rotation.y;
//   this.mesh.rotateZ(.5 * delta);
//
// };