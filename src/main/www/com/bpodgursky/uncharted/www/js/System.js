var orbitMaterial = new THREE.LineBasicMaterial({
  color: 0x33cc33,
  depthWrite: false
});

var scaleMaterial = new THREE.LineBasicMaterial({
  color: 0xbb33ff,
  depthWrite: false
});

var transparentMaterial = new THREE.MeshBasicMaterial({
  transparent: true
});

function System(position, planets) {

  this.object = new THREE.Group();
  var object = this.object;

  this.object.position.x = position.x;
  this.object.position.y = position.y;
  this.object.position.z = position.z;

  var axes = [];

  planets.forEach(function (planet) {

    var eccentricity = planet.values.ECCENTRICTY.value;
    var major = planet.values.SEMI_MAJOR_AXIS_LYS.value;
    var minor = major * Math.sqrt(1 - Math.pow(eccentricity, 2));

    axes.push(major);

    var ellipse = new THREE.EllipseCurve(0, 0, major, minor, 0, 2.0 * Math.PI, false);
    var ellipsePath = new THREE.CurvePath();
    ellipsePath.add(ellipse);
    var ellipseGeometry = ellipsePath.createPointsGeometry(100);
    ellipseGeometry.computeTangents();
    var line = new THREE.Line(ellipseGeometry, orbitMaterial);
    object.add(line);

    var simpleMaterial = new THREE.MeshBasicMaterial({
      color: 0x33ccff
    });

    // var material = new THREE.ShaderMaterial({
    //   // uniforms: {
    //   //   time: uniforms.time,
    //   //   scale: uniforms.scale,
    //   //   highTemp: {type: "f", value: star.temperatureEstimate},
    //   //   lowTemp: {type: "f", value: star.temperatureEstimate / 4}
    //   // },
    //   // vertexShader: starVertexShader,
    //   // fragmentShader: starFragmentShader,
    //   transparent: false
    // });


    var planetMesh = new THREE.Mesh(SPHERE_GEOMETRY, simpleMaterial);
    planetMesh.scale.x = planetMesh.scale.y = planetMesh.scale.z = planet.values.RADIUS_LYS.value;

    var surround = new THREE.Mesh(SPHERE_GEOMETRY, transparentMaterial);
    surround.scale.set(30000, 30000, 30000);

    planetMesh.add(surround);

    planetMesh.position.x = major;
    object.add(planetMesh);

  });

  var maxDistance = Math.max.apply(null, axes);

  var geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(0, 0, 0));
  geometry.vertices.push(new THREE.Vector3(maxDistance, 0, 0));

  var lineMesh = new THREE.Line(geometry, scaleMaterial);

  object.add(lineMesh);

}