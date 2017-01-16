var orbitMaterial = new THREE.LineBasicMaterial({
  color: 0x009900,
  depthWrite: false
});

var scaleMaterial = new THREE.LineBasicMaterial({
  color: 0xbb33ff,
  depthWrite: false
});

var transparentMaterial = new THREE.MeshBasicMaterial({
  transparent: true,
  color: 0x000000,
  depthWrite: false,
  renderOrder: 1,
  opacity: 0.0
});

var TRANSPARENT_GEOMETRY = new THREE.SphereGeometry(1.0, 16, 16);
var DETAIL_GEOMETRY = new THREE.SphereGeometry(1.0, 64, 64);

function System(star, position, planets, lookAt) {

  this.object = new THREE.Group();
  this.star = star;
  this.planets = planets;
  var object = this.object;

  //  since exoplanet orientations are with respect to earth
  this.object.lookAt(lookAt);

  var starData = star.objectData;

  var material = new THREE.ShaderMaterial({
    uniforms: {
      time: uniforms.time,
      scale: uniforms.scale,
      highTemp: {type: "f", value: starData.temperatureEstimate.value.quantity},
      lowTemp: {type: "f", value: starData.temperatureEstimate.value.quantity / 4}
    },
    vertexShader: shaders.dynamicVertexShader,
    fragmentShader: shaders.starFragmentShader,
    transparent: false
  });

  var starDetail = new THREE.Mesh(DETAIL_GEOMETRY, material);
  starDetail.objectData = starData;
  starDetail.scale.x = starDetail.scale.y = starDetail.scale.z = starData.radius.value.quantity;

  object.add(starDetail);

  var pointLight = new THREE.PointLight(0xffffff, 0.9, 1, 2);
  pointLight.position.set(0, 0, 0);

  object.add(pointLight);

  this.selectable = [starDetail];

  this.objectsByID = {};
  this.objectsByID[star.objectData.primaryId] = star;


  this.object.position.x = position.x;
  this.object.position.y = position.y;
  this.object.position.z = position.z;

  this.populated = false;
}

var DEGREE_IN_RADIANS = Math.PI / 180;

System.prototype.populatePlanets = function () {

  if (!this.populated) {
    this.populated = true;

    // var axes = [];

    var planetSelectable = this.selectable;
    var planetsByID = this.objectsByID;
    var object = this.object;

    this.planets.forEach(function (planet) {

      var major = planet.semiMajorAxisLys.value.quantity;
      var minor = planet.semiMinorAxisLys.value.quantity;

      var focusOffset = Math.sqrt(Math.pow(major / 2, 2) - Math.pow(minor / 2, 2));

      var planetPos = -major + focusOffset;

      var ellipse = new THREE.EllipseCurve(focusOffset, 0, major, minor, 0, 2.0 * Math.PI, false);
      var ellipsePath = new THREE.CurvePath();
      ellipsePath.add(ellipse);
      var ellipseGeometry = ellipsePath.createPointsGeometry(100);
      ellipseGeometry.computeTangents();
      var orbit = new THREE.Line(ellipseGeometry, orbitMaterial);

      orbit.rotation.set(0, 0, 0);

      //  I think this is right, but geometry is hard
      orbit.rotateZ(DEGREE_IN_RADIANS * planet.longAscendingNode.value.quantity);
      orbit.rotateY(DEGREE_IN_RADIANS * planet.inclination.value.quantity);
      orbit.rotateZ(DEGREE_IN_RADIANS * planet.argumentPeriapsis.value.quantity);

      var planetMesh = getDetailMesh(planet);

      var surround = new THREE.Mesh(TRANSPARENT_GEOMETRY, transparentMaterial);

      var distScale = planet.semiMajorAxisLys.value.quantity / 1.58e-5;
      var invScale = (7.3896e-9 / planet.radius.value.quantity) *
        distScale *
        140;

      surround.scale.set(invScale, invScale, invScale);
      surround.objectData = planet;

      planetMesh.objectData = planet;
      planetMesh.add(surround);

      planetMesh.position.set(planetPos,0,0);
      orbit.add(planetMesh);
      object.add(orbit);

      planetSelectable.push(surround);
      planetsByID[planet.primaryId] = planetMesh;


      var geometry = new THREE.Geometry();
      geometry.vertices.push(new THREE.Vector3(0, 0, 0));
      geometry.vertices.push(new THREE.Vector3(planetPos, 0, 0));

      var lineMesh = new THREE.Line(geometry, scaleMaterial);

      orbit.add(lineMesh);


    });

  }
};

