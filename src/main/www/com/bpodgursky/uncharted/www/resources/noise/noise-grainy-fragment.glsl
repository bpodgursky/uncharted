
			varying vec3 vTexCoord3D;

      uniform float highTemp;
      uniform float lowTemp;

			uniform float time;

//			uniform vec4 colors[1024];

			vec4 permute( vec4 x ) {

				return mod( ( ( x * 34.0 ) + 1.0 ) * x, 289.0 );

			}

			vec4 taylorInvSqrt( vec4 r ) {

				return 1.79284291400159 - 0.85373472095314 * r;

			}

			float snoise( vec3 v ) {

				const vec2 C = vec2( 1.0 / 6.0, 1.0 / 3.0 );
				const vec4 D = vec4( 0.0, 0.5, 1.0, 2.0 );

				// First corner

				vec3 i  = floor( v + dot( v, C.yyy ) );
				vec3 x0 = v - i + dot( i, C.xxx );

				// Other corners

				vec3 g = step( x0.yzx, x0.xyz );
				vec3 l = 1.0 - g;
				vec3 i1 = min( g.xyz, l.zxy );
				vec3 i2 = max( g.xyz, l.zxy );

				//  x0 = x0 - 0. + 0.0 * C
				vec3 x1 = x0 - i1 + 1.0 * C.xxx;
				vec3 x2 = x0 - i2 + 2.0 * C.xxx;
				vec3 x3 = x0 - 1. + 3.0 * C.xxx;

				// Permutations

				i = mod( i, 289.0 );
				vec4 p = permute( permute( permute(
						 i.z + vec4( 0.0, i1.z, i2.z, 1.0 ) )
					   + i.y + vec4( 0.0, i1.y, i2.y, 1.0 ) )
					   + i.x + vec4( 0.0, i1.x, i2.x, 1.0 ) );

				// Gradients
				// ( N*N points uniformly over a square, mapped onto an octahedron.)

				float n_ = 1.0 / 7.0; // N=7

				vec3 ns = n_ * D.wyz - D.xzx;

				vec4 j = p - 49.0 * floor( p * ns.z *ns.z );  //  mod(p,N*N)

				vec4 x_ = floor( j * ns.z );
				vec4 y_ = floor( j - 7.0 * x_ );    // mod(j,N)

				vec4 x = x_ *ns.x + ns.yyyy;
				vec4 y = y_ *ns.x + ns.yyyy;
				vec4 h = 1.0 - abs( x ) - abs( y );

				vec4 b0 = vec4( x.xy, y.xy );
				vec4 b1 = vec4( x.zw, y.zw );

				vec4 s0 = floor( b0 ) * 2.0 + 1.0;
				vec4 s1 = floor( b1 ) * 2.0 + 1.0;
				vec4 sh = -step( h, vec4( 0.0 ) );

				vec4 a0 = b0.xzyw + s0.xzyw * sh.xxyy;
				vec4 a1 = b1.xzyw + s1.xzyw * sh.zzww;

				vec3 p0 = vec3( a0.xy, h.x );
				vec3 p1 = vec3( a0.zw, h.y );
				vec3 p2 = vec3( a1.xy, h.z );
				vec3 p3 = vec3( a1.zw, h.w );

				// Normalise gradients

				vec4 norm = taylorInvSqrt( vec4( dot(p0,p0), dot(p1,p1), dot(p2, p2), dot(p3,p3) ) );
				p0 *= norm.x;
				p1 *= norm.y;
				p2 *= norm.z;
				p3 *= norm.w;

				// Mix final noise value

				vec4 m = max(0.6 - vec4(dot(x0,x0), dot(x1,x1), dot(x2,x2), dot(x3,x3) ), 0.0 );
				m = m * m;
				return 42.0 * dot( m*m, vec4( dot(p0,x0), dot(p1,x1),
											dot(p2,x2), dot(p3,x3) ) );

			}


const int octaves = 4;

 float noise(vec3 position, float frequency, float persistence) {
    float total = 0.0; // Total value so far
    float maxAmplitude = 0.0; // Accumulates highest theoretical amplitude
    float amplitude = 1.0;
    for (int i = 0; i < octaves; i++) {
        // Get the noise sample
        total += snoise(position * frequency) * amplitude;
        // Make the wavelength twice as small
        frequency *= 2.0;
        // Add to our maximum possible amplitude
        maxAmplitude += amplitude;
        // Reduce amplitude according to persistence for the next octave
        amplitude *= persistence;
    }
    // Scale the result by the maximum amplitude
    return total / maxAmplitude;
}

    void main( void ) {

      float noiseBase = (noise(vTexCoord3D , 4.0, 0.7)+1.0)/2.0;

       // Sunspots
      float frequency = 0.5;
      float t1 = snoise(vTexCoord3D * frequency) - .4;

      float ss = max(0.0, t1);
      float total = noiseBase - ss;

      float temp = (highTemp * (total)  +(1.0-total) * lowTemp);

      //     var indexHigh = Math.floor((star.temperatureEstimate - 800.0) * spectrumData.width / 29200.0) * 4+8;
      float i =(temp - 800.0)*1024.0/29200.0;


      //  for R
      bool rbucket1 = (i) < 60.0;   //  0, 255 in 60
      bool rbucket2 = (i) >= 60.0 && (i) < 236.0;  //   255,255
      bool rbucket3 = (i) >= 236.0 && (i) < 288.0; //  255,128
      bool rbucket4 = (i) >= 288.0 && (i) < 377.0; //  128,60
      bool rbucket5 = (i) >= 377.0 && (i) < 511.0; //  60,0
      bool rbucket6 = (i) >= 511.0;  //  0,0

      bool gbucket1 = float(i) <60.0;
      bool gbucket2 = float(i) >= 60.0 && float(i) < 103.0; //  0,100
      bool gbucket3 = float(i) >= 103.0 && float(i) < 133.0; // 100,233
      bool gbucket4 = float(i) >= 133.0 && float(i) < 174.0; // 233, 255
      bool gbucket5 = float(i) >= 174.0 && float(i) < 236.0; // 255,255
      bool gbucket6 = float(i) >= 236.0 && float(i) < 286.0; //255,193
      bool gbucket7 = float(i) >= 286.0 && float(i) < 367.0; //193,129
      bool gbucket8 = float(i) >= 367.0 && float(i) < 511.0; //129,64
      bool gbucket9 = float(i) >= 511.0; // 64,32

     // for B
      bool bbucket1 = float(i) < 103.0;
      bool bbucket2 = float(i) >= 103.0 && float(i) < 133.0; // 0,211
      bool bbucket3 = float(i) >= 133.0 && float(i) < 173.0; // 211,247
      bool bbucket4 = float(i) >= 173.0 && float(i) < 231.0;  //  247,255
      bool bbucket5 = float(i )>= 231.0;

//      i = 0.0;

      float r =
        float(rbucket1) * (0.0 + (i) * (255.0-0.0)/60.0) +
        float(rbucket2) * (255.0) +
        float(rbucket3) * (255.0 + (i - 236.0) * (128.0 - 255.0)/(288.0-236.0)) +
        float(rbucket4) * (128.0 + (i - 288.0) * (60.0 - 128.0)/(377.0 - 288.0)) +
        float(rbucket5) * (60.0 + (i - 377.0) * (0.0 - 60.0)/(511.0-377.0))+
        float(rbucket6) * 0.0;

      float g =
         float(gbucket1) * (0.0) +
         float(gbucket2) * (0.0 + (i - 60.0) *(100.0-0.0)/(103.0-60.0)) +
         float(gbucket3) * (100.0 + (i - 103.0) *(233.0- 100.0)/(133.0-103.0))+
         float(gbucket4) * (233.0 + (i - 133.0) *(255.0 - 233.0)/(174.0 - 133.0))+
         float(gbucket5) * (255.0) +
         float(gbucket6) * (255.0 +(i - 236.0) *(193.0 - 255.0)/(286.0 - 236.0)) +
         float(gbucket7) * (193.0 + (i - 286.0) * (129.0 - 193.0)/(367.0 - 286.0)) +
         float(gbucket8) * (129.0 + (i - 367.0) * (64.0 - 129.0)/(511.0 - 367.0))+
         float(gbucket9) * (64.0 + (i - 511.0) * (32.0-64.0)/(1024.0 - 511.0));

      float b =
        float(bbucket1) * 0.0+
        float(bbucket2) * (0.0 + (i - 103.0) * (211.0 - 0.0)/(133.0-103.0)) +
        float(bbucket3) * (211.0 + (i - 133.0) * (247.0-211.0)/(173.0-133.0))+
        float(bbucket4) * (247.0 + (i - 173.0)*(255.0-247.0)/(231.0-173.0))+
        float(bbucket5) * 255.0;

      gl_FragColor = vec4(vec3(r/255.0, g/255.0, b/255.0), 1.0);



//      if(t1 > .3){
//gl_FragColor = vec4(vec3(rHigh, gHigh, bHigh), aHigh);
//
//}else{
//gl_FragColor = vec4(vec3(rLow, gLow, bLow), aLow);
//}

//vec3 minus = vec3(total, total, total);
//        vec3 normColor = normalize(vec3(r, g, b)) - minus;
//
//        gl_FragColor = vec4(normColor, 255.0);


			}


