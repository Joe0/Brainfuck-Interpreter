# Brainfuck-Interpreter

A basic Brainfuck interpreter
***
## Usage
scala Interpreter file [initial memory] [memory increment]

initial memory - This is the amount of memory, in bytes, initially allocated for your application.<br>
memory increment - This is the amount of memory, in bytes, that your application allocates once it has reached it's capacity.

## Examples
This just runs jabh.bf.<br>
scala Interpreter examples/jabh.bf

This runs jabh.bf and sets the initial size to 1 and increases memory in chunks of 8 bytes.<br>
scala Interpreter examples/jabh.bf 1 8

This runs helloworld.bf and requires no addition of memory.<br>
scala Interpreter examples/helloworld.bf 5