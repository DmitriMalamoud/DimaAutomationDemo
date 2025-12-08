#!/bin/bash

/bin/ollama serve &

pid=$!


sleep 5

echo "Retrieve PHI3 model..."
ollama pull phi3
echo "Done!"

# Wait for Ollama process to finish.
wait $pid
