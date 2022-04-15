# ProcessManager

## Description

Follows the program, due to the status command of this program.

If a keyword/phrase is found in the program logs, it executes the suggested command.

## Usage

Run command: `java -jar ProcessManager-0.0.1-SNAPSHOT.jar [arg1] [arg2] [arg3]`

The program takes 3 required arguments:

1. First program status argument, example: `"pxf cluster status"`
2. The second argument is a word/phrase to search the logs for a signal that the program is not working or needs to be restarted, for example: `"ERROR"`
3. The third argument is the command that will be executed if the keyword/phrase from the second argument is found, example: `"pxf cluster start"`

## Important Details

* Status check occurs every 5 seconds.
* The command of the third argument will be executed every time a word/phrase from the second argument is seen.
