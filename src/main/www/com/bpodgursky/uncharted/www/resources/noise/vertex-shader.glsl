			uniform float time;
			uniform float scale;

			varying vec3 vTexCoord3D;
			varying vec3 vNormal;
			varying vec3 vViewPosition;

			void main( void ) {

				vec4 mPosition = modelMatrix * vec4( position, 1.0 );
				vNormal = normalize( normalMatrix * normal );
				vViewPosition = cameraPosition - mPosition.xyz;

				vTexCoord3D = scale * ( position.xyz + vec3( 0.0, 0.0, time ) );
				gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );

			}