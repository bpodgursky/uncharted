function Shaders() {

  this.starFragmentShader = null;
  this.starVertexShader = null;
  this.coronaFragmentShader = null;
  this.coronaVertexShader = null;

}

Shaders.prototype.loaders = function() {

  var obj = this;

  return [$.ajax({
    type: 'GET',
    dataType: 'html',
    url: "resources/noise/noise-grainy-fragment.glsl",
    success: function (frag) {
      obj.starFragmentShader = frag;
    }
  }),
    $.ajax({
      type: 'GET',
      dataType: 'html',
      url: "resources/noise/vertex-shader.glsl",
      success: function (vert) {
        obj.starVertexShader = vert;
      }
    }),
    $.ajax({
      type: 'GET',
      dataType: 'html',
      url: "resources/noise/corona-vertex-shader.glsl",
      success: function (vert) {
        obj.coronaVertexShader = vert;
      }
    }),
    $.ajax({
      type: 'GET',
      dataType: 'html',
      url: "resources/noise/corona-fragment-shader.glsl",
      success: function (vert) {
        obj.coronaFragmentShader = vert;
      }
    }),
    $.ajax({
      type: 'GET',
      dataType: 'html',
      url: "resources/noise/planets/default-planet-fragment-shader.glsl",
      success: function (vert) {
        obj.defaultPlanetFragmentShader = vert;
      }
    }),
    $.ajax({
      type: 'GET',
      dataType: 'html',
      url: "resources/noise/planets/default-planet-vertex-shader.glsl",
      success: function (vert) {
        obj.defaultPlanetVertexShader = vert;
      }
    }),
    $.ajax({
      type: 'GET',
      dataType: 'html',
      url: "resources/noise/planets/test-texture-fragment-shader.glsl",
      success: function (vert) {
        obj.testTexturePlanetFragmentShader = vert;
      }
    })

  ]

};