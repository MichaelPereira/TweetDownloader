cd $1
kill `cat toto.txt`
rm toto.txt

# No need to relaunch the consumer because a loop is relaunching it automatically on the server
# java -Xms$2 -Xmx$3 -jar $4