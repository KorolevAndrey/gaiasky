uniform float u_alpha;

varying vec4 v_col;
void main() {
    gl_FragColor = vec4(v_col.rgb, v_col.a * u_alpha);
}
