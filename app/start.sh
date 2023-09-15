#! /bin/zsh
docker rmi sanbox

WORK_DIR="/home/jmzero/app"

DOCKERFILE_DIR=$WORK_DIR
INPUT_DIR="$WORK_DIR/code"
OUTPUT_DIR=$WORK_DIR

docker build -t sanbox $DOCKERFILE_DIR
isBuild=$?

if [ $isBuild -eq 0 ]
then
	cat "$INPUT_DIR/input"  | docker run -i --rm sanbox > "$OUTPUT_DIR/output"
else
	echo "build failed"
fi
