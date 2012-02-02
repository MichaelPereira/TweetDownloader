cd $1
kill `cat toto.txt`
rm toto.txt
java -Xms$2 -Xmx$3 -jar $4