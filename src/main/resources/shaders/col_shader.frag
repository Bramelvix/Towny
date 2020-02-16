#version 330 core

out vec4 FragColor;
uniform vec4 i_color;

void main() {
    FragColor = i_color;
}