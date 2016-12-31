//  TODO this is 99.9% unscientific.  I'm using this SO answer http://astronomy.stackexchange.com/questions/13382/planets-classification-by-density?rq=1
//  to say rocky < .1 jupiter masses.  also the shaders are super crude placeholders.
function getShader(planetData) {
  console.log(planetData);

  if (planetData.massKg.value.quantity < 1.898e26) { //  10% jupiter
    return shaders.rockyPlanetFragmentShader;
  } else {
    return shaders.gasPlanetFragmentShader;
  }
}


var NAME_TO_MAP = {
  Earth: "images/planets/earthmap1k.jpg"
}

function getMaterial(planetData) {

  var planetMap = NAME_TO_MAP[planetData.properName];

  if (planetMap) {

    return new THREE.MeshLambertMaterial({
      map: THREE.ImageUtils.loadTexture(planetMap)
    });

  } else {
    return new THREE.ShaderMaterial({
      uniforms: {
        time: uniforms.time,
        scale: uniforms.scale
      },
      vertexShader: shaders.staticVertexShader,
      fragmentShader: getShader(planetData),
      transparent: false
    });

  }

}

function getDetailMesh(planetData) {

  var planetMesh = new THREE.Mesh(DETAIL_GEOMETRY, getMaterial(planetData));
  planetMesh.scale.x = planetMesh.scale.y = planetMesh.scale.z = planetData.radius.value.quantity;

  planetMesh.rotateY(Math.PI / 2);
  planetMesh.rotateZ(- Math.PI / 2);

  return planetMesh;
}