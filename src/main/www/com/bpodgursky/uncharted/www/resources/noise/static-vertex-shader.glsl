uniform float time;
uniform float scale;

varying vec3 vTexCoord3D;

void main( void ) {
  vTexCoord3D = scale * ( position.xyz );
  gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );
}