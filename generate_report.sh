#!/bin/bash

# Function to generate log filename with timestamp
generate_log_filename() {
    echo "logs/build_failed_$(date +"%Y%m%d_%H%M%S").log"
}

# Main script starts here
log_file=$(generate_log_filename)

# Write the build failure log content to the file
echo "Build failed at: $(date)" >> "$log_file"
echo "Build console output:" >> "$log_file"
echo "======================" >> "$log_file"
cat "pipeline.log" >> "$log_file"
