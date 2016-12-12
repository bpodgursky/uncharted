var orbitMaterial = new THREE.LineBasicMaterial({
  color: 0x33cc33,
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

function System(star, position, planets) {

  this.object = new THREE.Group();
  this.star = star;
  var object = this.object;

  var starData = star.objectData;

  var material = new THREE.ShaderMaterial({
    uniforms: {
      time: uniforms.time,
      scale: uniforms.scale,
      highTemp: {type: "f", value: starData.temperatureEstimate},
      lowTemp: {type: "f", value: starData.temperatureEstimate / 4}
    },
    vertexShader: shaders.vertexShader,
    fragmentShader: shaders.starFragmentShader,
    transparent: false
  });

  var starDetail = new THREE.Mesh(DETAIL_GEOMETRY, material);
  starDetail.objectData = starData;
  starDetail.scale.x = starDetail.scale.y = starDetail.scale.z = starData.radiusInLys;

  object.add(starDetail);

  this.selectable = [starDetail];
  var planetSelectable = this.selectable;

  this.objectsByID = {};
  this.objectsByID[star.objectData.primaryId] = star;

  var planetsByID = this.objectsByID;

  this.object.position.x = position.x;
  this.object.position.y = position.y;
  this.object.position.z = position.z;

  var axes = [];

  planets.forEach(function (planet) {

    var eccentricity = planet.values.ECCENTRICTY.value;
    var major = planet.values.SEMI_MAJOR_AXIS_LYS.value;
    var minor = major * Math.sqrt(1 - Math.pow(eccentricity, 2));

    var focusOffset = Math.sqrt(Math.pow(major/2, 2) - Math.pow(minor/2, 2));

    axes.push(major+focusOffset);

    var ellipse = new THREE.EllipseCurve(focusOffset, 0, major, minor, 0, 2.0 * Math.PI, false);
    var ellipsePath = new THREE.CurvePath();
    ellipsePath.add(ellipse);
    var ellipseGeometry = ellipsePath.createPointsGeometry(100);
    ellipseGeometry.computeTangents();
    var line = new THREE.Line(ellipseGeometry, orbitMaterial);
    object.add(line);

    // var simpleMaterial = new THREE.MeshBasicMaterial({
    //   color: 0x33ccff
    // });

    var material = new THREE.ShaderMaterial({
      uniforms: {
        time: uniforms.time,
        scale: uniforms.scale
      },
      vertexShader: shaders.vertexShader,
      fragmentShader: shaders.rockyPlanetFragmentShader,
      transparent: false
    });

    var planetMesh = new THREE.Mesh(DETAIL_GEOMETRY, material);
    planetMesh.scale.x = planetMesh.scale.y = planetMesh.scale.z = planet.values.RADIUS_LYS.value;

    var surround = new THREE.Mesh(TRANSPARENT_GEOMETRY, transparentMaterial);
    surround.scale.set(10, 10, 10);
    surround.objectData = planet;

    planetMesh.objectData = planet;
    planetMesh.add(surround);

    planetMesh.position.x = major+focusOffset;
    object.add(planetMesh);

    planetSelectable.push(surround);
    planetsByID[planet.primaryId] = planetMesh;

  });

  var maxDistance = Math.max.apply(null, axes);

  var geometry = new THREE.Geometry();
  geometry.vertices.push(new THREE.Vector3(0, 0, 0));
  geometry.vertices.push(new THREE.Vector3(maxDistance, 0, 0));

  var lineMesh = new THREE.Line(geometry, scaleMaterial);

  object.add(lineMesh);

}