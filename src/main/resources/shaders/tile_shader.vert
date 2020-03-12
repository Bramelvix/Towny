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
    vec2 scaled = aPos * scale;
    vec3 translated = vec3(scaled, 0) + aInstancePos + vec3(offset, 0);
    gl_Position = vec4(translated, 1.0);

    vec2 scaled_coord = (aTexCoords * tex_scale);
    vec2 translated_coord = (scaled_coord + aInstanceTexCoords);
    TexCoords = translated_coord;
}