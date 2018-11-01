# How to run

Open terminal, and make sure java is installed in your system.
Check that by running`java --version`
Run the following commands.
* `git clone https://github.com/shraiysh/Simplify-it.git`
* `cd Simplify-it`
* `javac LinearEquationSimplifier.java`
* `java LinearEquationSimplifier`

The resources are int the file `LinearEquationSimplifierResource.java`


# Simplify-it

The equation simplifier groups all the terms with same variables and gives a simplified linear equation.
A resource file is also provided for general utilities such as HashMap of variables with their coefficients.

# Examples

Here output#1 does not alter the coefficients, and output#2 is with constant=1


|                Input                |           Output#1                 |               Output#2         |
|:-----------------------------------:|:----------------------------------:|:------------------------------:|
|0.5x + 3y = 2.5x +3z + 2y -56 + 54-2 |      +4.0-2.0x+1.0y-3.0z=0         |       +1.0-0.5x+0.25y-0.75z=0  |
|2x+3y+5-4x=8y                        |       +5.0-2.0x-5.0y=0             |  +1.0-0.4x-1.0y=0|
