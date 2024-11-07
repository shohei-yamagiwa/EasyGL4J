/**
 * The MIT License (MIT)
 * <p>
 * Copyright @ 2024, Shohei Yamagiwa
 * </p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * </p>
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dev.shoheiyamagiwa.easygl4j.opengl.graphics.shader;

import dev.shoheiyamagiwa.easygl4j.Shader;

import static org.lwjgl.opengl.GL20.*;

/**
 * The {@code GLVertexShader} class represents a vertex shader in OpenGL.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public class GLVertexShader extends Shader {
    /**
     * Creates new shader with given vertex shader source
     *
     * @param source The source code of the vertex shader.
     */
    public GLVertexShader(CharSequence source) {
        super(glCreateShader(GL_VERTEX_SHADER),source);

        glShaderSource(getHandle(), source);
        glCompileShader(getHandle());
    }

    @Override
    public void dispose() {
        glDeleteShader(getHandle());
    }
}