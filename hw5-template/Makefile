ifdef OS
   	PATH_SEPARATOR = ;
else
	PATH_SEPARATOR = :
endif

JAVAC = javac
JVM = java
TARGET = build/
MKTARGET = mkdir -p $(TARGET)
REMOVE = rm -rf

JAVAC_FLAGS = -g -d $(TARGET)
JAVAC_CP = -cp

JARS = junit-4.12.jar$(PATH_SEPARATOR)hamcrest-core-1.3.jar
SRCS = paxos/*.java
TEST = paxos.PaxosTest
MAIN = org.junit.runner.JUnitCore

.SUFFIXES : .class .java

all:
	$(MKTARGET) | $(JAVAC) $(JAVAC_FLAGS) $(JAVAC_CP) "$(JARS)" $(SRCS)

test:
	$(JVM) $(JAVAC_CP) "$(JARS)$(PATH_SEPARATOR)$(TARGET)" $(MAIN) $(TEST)

clean:
	$(REMOVE) $(TARGET)

.PHONY: all test clean
