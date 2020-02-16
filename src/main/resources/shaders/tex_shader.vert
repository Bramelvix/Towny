#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoords;

out vec2 TexCoords;

uniform vec2 offset;
uniform vec2 scale;

void main() {

    vec3 scaled = aPos * vec3(scale, 1.0);
    vec3 translated = scaled + vec3(offset, 0.0);
    gl_Position = vec4(translated, 1.0);
    TexCoords = aTexCoords;
}