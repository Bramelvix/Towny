#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoords;
//layout (location = 2) in vec2 texCoordOffsets;

out vec2 TexCoords;

uniform vec2 scale;
uniform int rowLength;
uniform vec2 offset;
uniform vec2 texCoordOffsets[627]; //627 seems to be the highest
uniform vec2 tex_scale;

void main() {
    vec3 scaled = aPos;// * vec3(scale, 1.0);
    int yShit = gl_InstanceID/rowLength;
    int xShit = (-rowLength*yShit + gl_InstanceID);
    vec3 translated = scaled + (xShit*vec3(0.064f,0,0)) + (yShit*vec3(0,-0.11387900355f,0)) + vec3(offset,0);
    gl_Position = vec4(translated, 1.0);

    vec2 scaled_coord = (aTexCoords * tex_scale);
    vec2 translated_coord = (scaled_coord + texCoordOffsets[gl_InstanceID]);
    TexCoords = translated_coord;
}