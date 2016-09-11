        uniform float cur_time;
        uniform float beg_time;

        varying vec2 vUv;

        void main() {
            float full_time = 5000.;

            float time_left = cur_time - beg_time;
            float expl_step0 = 0.;
            float expl_step1 = 0.3;
            float expl_max   = 1.;

            float as0 = 0.;
            float as1 = 1.;
            float as2 = 0.;

            float time_perc = clamp( (time_left / full_time), 0., 1. ) ;
            float alphap;
            alphap = mix(as0,as1, smoothstep(expl_step0, expl_step1, time_perc));
            alphap = mix(alphap,as2, smoothstep(expl_step1, expl_max, time_perc));


            vec2 p = vUv;
            vec2 c = vec2(0.5, 0.5);
            float max_g = 1.;
            float dist = length(p - c) * 2. ;

            float step1 = 0.;
            float step2 = 0.2;
            float step3 = 0.3;

            vec4 color;
            float a0 = 1.;
            float a1 = 1.;
            float a2 = 0.7;
            float a3 = 0.0;


            vec4 c0 = vec4(1., 1., 1., a0 * alphap);
            vec4 c1 = vec4(0.9, 0.9, 1., a1 * alphap);
            vec4 c2 = vec4(0.7, 0.7, 1., a2 * alphap);
            vec4 c3 = vec4(0., 0., 0., 0.);



            color = mix(c0, c1, smoothstep(step1, step2, dist));
            color = mix(color, c2, smoothstep(step2, step3, dist));
            color = mix(color, c3, smoothstep(step3, max_g, dist));

            gl_FragColor = color;
   }