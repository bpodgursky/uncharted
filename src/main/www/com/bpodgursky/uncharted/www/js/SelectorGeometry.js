THREE.SelectorGeometry = function (scale, arrowWidth) {

  var arrowOffset = arrowWidth/2;

  THREE.Geometry.call(this);

  var scope = this;

  scope.vertices = [
    new THREE.Vector3(scale/2, 0, 0),
    new THREE.Vector3(scale, -arrowOffset, 0),
    new THREE.Vector3(scale, arrowOffset, 0),

    new THREE.Vector3(-scale/2, 0, 0),
    new THREE.Vector3(-scale, -arrowOffset, 0),
    new THREE.Vector3(-scale, arrowOffset, 0),

    new THREE.Vector3(0, scale/2, 0),
    new THREE.Vector3(-arrowOffset, scale, 0),
    new THREE.Vector3(arrowOffset, scale, 0),

    new THREE.Vector3(0, -scale/2, 0),
    new THREE.Vector3(-arrowOffset, -scale, 0),
    new THREE.Vector3(arrowOffset, -scale, 0)

  ];

  scope.faces = [
    new THREE.Face3(0, 1, 2),
    new THREE.Face3(3, 4, 5),
    new THREE.Face3(6, 7, 8),
    new THREE.Face3(9, 10, 11)
  ];

  this.computeFaceNormals();
  this.computeVertexNormals();

};

THREE.SelectorGeometry.prototype = Object.create(THREE.Geometry.prototype);