#version 330 core

layout (location = 0) in vec2 aPos;
layout (location = 1) in vec2 aTexCoords;
layout (location = 2) in vec3 aInstancePos; //Position of the instance
layout (location = 3) in vec2 aInstanceTexCoords; //Texture coordinates of the instance

out vec2 TexCoords;

uniform vec2 scale;
uniform vec2 offset;
uniform vec2 tex_scale;

void main() {
    vec3 scaled = vec3(aPos, aInstancePos.z) * vec3(scale, 1);
    vec3 translated = scaled + vec3(aInstancePos.xy, 0.f) + vec3(offset, 0);
    gl_Position = vec4(translated, 1.0);

    vec2 scaled_coord = (aTexCoords * tex_scale);
    vec2 translated_coord = (scaled_coord + aInstanceTexCoords);
    TexCoords = translated_coord;
}