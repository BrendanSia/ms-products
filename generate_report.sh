#!/bin/bash

# Function to generate log filename with timestamp
generate_log_filename() {
    echo "reports/build_failure_log_$(date +"%Y%m%d_%H%M%S").log"
}

# Main script starts here
log_file=$(generate_log_filename)

# Check if a previous log file exists and overwrite it with a new one
if [ -f "$log_file" ]; then
    echo "Previous log file exists. Overwriting..."
    rm "$log_file"
fi

# Write the build failure log content to the file
echo "Build failed at: $(date)" >> "$log_file"
echo "Build console output:" >> "$log_file"
echo "======================" >> "$log_file"
cat >> "$log_file"
