varying vec3 sPos;
uniform float scale;

void main() {
  sPos = position;
  gl_Position = projectionMatrix * (modelViewMatrix * vec4(0.0, 0.0, 0.0, 1.0) + vec4(position.x, position.y, 0.0, 0.0));
}