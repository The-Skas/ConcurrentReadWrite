A Java Programming excercise to try out multi-threaded programming through creating a simple Read/Write.

Multiple Read Threads can occur at the same time, given that there are no Writes being done.

But only One Write can be done at one at a time, given that there are no threads Reading.

An issue is thread starvation, which should be resolved with fairness.