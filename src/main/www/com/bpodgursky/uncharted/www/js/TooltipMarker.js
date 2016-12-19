//  TODO this is an abomination
function TooltipMarker(label, distanceFormatted, radius) {

  this.object = new THREE.Group();

  var bitmap = document.createElement('canvas');
  bitmap.width = 510;
  bitmap.height = 510;

  var g = bitmap.getContext('2d');

  g.font = 'Bold 30px Arial';
  var starLen = g.measureText(label).width;
  var distLen = g.measureText(distanceFormatted).width;

  var bitWidthMax = (Math.max(starLen, distLen) + 20);
  var bitHeightMax = 85;

  var tipWidth = bitWidthMax / 510;
  var tipHeigh = bitHeightMax / 510;

  g.fillStyle = '#006321';
  g.fillRect(0, 0, bitWidthMax, bitmap.height);

  g.fillStyle = '#010154';
  g.fillRect(2, bitmap.height - bitHeightMax + 2 , bitWidthMax - 4, bitHeightMax - 4);

  g.fillStyle = 'white';
  g.fillText(label, 10, 455);
  g.fillText(distanceFormatted, 10, 495);

  var texture = new THREE.Texture(bitmap);
  texture.magFilter = THREE.NearestFilter;
  texture.minFilter = THREE.NearestFilter;
  texture.needsUpdate = true;

  var rectShape = new THREE.Shape();

  rectShape.moveTo(0, 0);
  rectShape.lineTo(tipWidth, 0);
  rectShape.lineTo(tipWidth, tipHeigh);
  rectShape.lineTo(0, tipHeigh);

  var rectGeom = new THREE.ShapeGeometry(rectShape);
  this.rectMesh = new THREE.Mesh(rectGeom, new THREE.MeshBasicMaterial({
    transparent: true,
    opacity: 0.8,
    map: texture
  }));

  var lineMaterial = new THREE.LineBasicMaterial({
    color: 0x006321
  });

  var geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(0, 0, 0));
  geometry.vertices.push(new THREE.Vector3(.05, 0, .01));
  var labelLine = new THREE.Line(geometry, lineMaterial);

  this.scaleObj = new THREE.Group();
  this.scaleObj.add(this.rectMesh);
  this.scaleObj.add(labelLine);

  this.scaleObj.position.x = radius;

  this.object.add(this.scaleObj);

  this.rectMesh.position.x = .05;
  this.rectMesh.position.y = - (85/510)/2;
  this.rectMesh.position.z = .01;

}

TooltipMarker.prototype.updateTo = function (camera) {

  var tooltipDistance = this.object.position.distanceTo(camera.position);
  var scale = .4 * tooltipDistance;

  this.scaleObj.scale.x = scale;
  this.scaleObj.scale.y = scale;
  this.scaleObj.scale.z = scale;

  this.object.rotation.x = camera.rotation.x;
  this.object.rotation.y = camera.rotation.y;
  this.object.rotation.z = camera.rotation.z;

};