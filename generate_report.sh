#!/bin/bash

# Path to the directory where XML reports are stored
REPORT_DIR="reports"

# Check if the directory exists, if not create it
mkdir -p "$REPORT_DIR"

# Define the filename for the report
REPORT_FILENAME="failure_report_$(date +"%Y%m%d_%H%M%S").xml"

# Combine directory and filename to get the full path
REPORT_PATH="$REPORT_DIR/$REPORT_FILENAME"

# Check if a previous report exists
if [ -f "$REPORT_PATH" ]; then
    # If previous report exists, overwrite it with a new one
    echo "Previous report exists. Overwriting..."
    rm "$REPORT_PATH"
fi

# Generate XML content for the report
echo "<failureReport>" >> "$REPORT_PATH"
echo "    <timestamp>$(date +"%Y-%m-%d %H:%M:%S")</timestamp>" >> "$REPORT_PATH"
echo "    <uniqueIdentifier>$(uuidgen)</uniqueIdentifier>" >> "$REPORT_PATH"
echo "    <!-- Add more information here -->" >> "$REPORT_PATH"
echo "</failureReport>" >> "$REPORT_PATH"

echo "New report generated: $REPORT_PATH"