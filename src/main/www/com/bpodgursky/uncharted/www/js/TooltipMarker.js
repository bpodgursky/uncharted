function TooltipMarker(label, distance, position) {

  var distanceFormatted = distance.toFixed(1) + "ly";

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

  g.fillStyle = '#00b33c';
  g.fillRect(0, 0, bitWidthMax, bitmap.height);

  //  + bitHeightMax/2
  g.fillStyle = '#000066';
  g.fillRect(3, bitmap.height - bitHeightMax + 3 , bitWidthMax - 6, bitHeightMax - 6);

  g.fillStyle = 'white';
  g.fillText(label, 10, 455);// +bitHeightMax/2
  g.fillText(distanceFormatted, 10, 495);// +bitHeightMax/2

  var texture = new THREE.Texture(bitmap);
  texture.magFilter = THREE.NearestFilter;
  texture.minFilter = THREE.NearestFilter;
  texture.needsUpdate = true;

  var rectShape = new THREE.Shape();
  //rectShape.moveTo(0, -tipHeigh/2);
  //rectShape.lineTo(tipWidth, - tipHeigh/2);
  //rectShape.lineTo(tipWidth, tipHeigh/2);
  //rectShape.lineTo(0, tipHeigh/2);

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
    color: 0x00b33c
  });

  var geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(0, 0, 0));
  geometry.vertices.push(new THREE.Vector3(.08, 0, 0));
  var labelLine = new THREE.Line(geometry, lineMaterial);

  this.object.add(this.rectMesh);
  this.object.add(labelLine);

  //this.distanceLabel.position.x = .08;
  //this.labelMesh.position.x = .08;
  this.rectMesh.position.x = .08;

  this.object.position.set(
      position.x,
      position.y,
      position.z
  );

}

TooltipMarker.prototype.updateTo = function (camera) {

  var tooltipDistance = this.object.position.distanceTo(camera.position);
  var scale = .4 * tooltipDistance;

  //this.distanceLabel.scale.x = scale;
  //this.distanceLabel.scale.y = scale;
  //this.distanceLabel.scale.z = scale;
  //
  //this.distanceLabel.position.y = -1*scale;
  //
  //this.labelMesh.scale.x = scale;
  //this.labelMesh.scale.y = scale;
  //this.labelMesh.scale.z = scale;

  this.rectMesh.scale.x = scale;
  this.rectMesh.scale.y = scale;
  this.rectMesh.scale.z = scale;

  this.object.rotation.x = camera.rotation.x;
  this.object.rotation.y = camera.rotation.y;
  this.object.rotation.z = camera.rotation.z;

};