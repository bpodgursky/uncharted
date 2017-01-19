//  TODO this is 99.9% unscientific.  I'm using this SO answer http://astronomy.stackexchange.com/questions/13382/planets-classification-by-density?rq=1
//  to say rocky < .1 jupiter masses.  also the shaders are super crude placeholders.
function getShader(planetData) {
  if (planetData.massKg.value.quantity < 1.898e26) { //  10% jupiter
    return shaders.rockyPlanetFragmentShader;
  } else {
    return shaders.gasPlanetFragmentShader;
  }
}

//  images are all from http://planetpixelemporium.com/planets.html
var NAME_TO_MAP = {
  Earth: "images/planets/earthmap1k.jpg",
  Mercury: "images/planets/mercurymap.jpg",
  Venus: "images/planets/venusmap.jpg",
  Mars: "images/planets/marsmap1k.jpg",
  Jupiter: "images/planets/jupiter2_4k.jpg",
  Saturn: "images/planets/saturnmap.jpg",
  Uranus: "images/planets/uranusmap.jpg",
  Neptune: "images/planets/neptunemap.jpg"
};

function getPlanet(planetData) {

  var planetMap = NAME_TO_MAP[planetData.properName];

  var axialTiltRadians = planetData.axialTilt.value.quantity * DEGREE_IN_RADIANS;

  if (planetMap) {

    return {
      material:new THREE.MeshLambertMaterial({
        map: THREE.ImageUtils.loadTexture(planetMap)
      }),
      rotation: {
        x: Math.PI / 2,
        y: 0,
        z: axialTiltRadians
    }};

  } else {
    return {
      material: new THREE.ShaderMaterial({
        uniforms: {
          time: uniforms.time,
          scale: uniforms.scale
        },
        vertexShader: shaders.staticVertexShader,
        fragmentShader: getShader(planetData),
        transparent: false
      }),
      rotation: {
        x: 0,
        y: Math.PI / 2,
        z: axialTiltRadians
      }
    };

  }

}

function getDetailMesh(planetData) {

  var planet = getPlanet(planetData);
  var planetMesh = new THREE.Mesh(DETAIL_GEOMETRY, planet.material);
  planetMesh.scale.x = planetMesh.scale.y = planetMesh.scale.z = planetData.radius.value.quantity;
  planetMesh.rotation.set(planet.rotation.x, planet.rotation.y, planet.rotation.z);

  return planetMesh;
}