cd $1
ps -el | grep "TweetConsumer-1.0-SNAPSHOT-jar-with-dependencies.jar" | grep -v "grep" | awk '{print $2}' > toto.txt