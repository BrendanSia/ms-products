#!/bin/bash

REPORT_DIR="reports"

# If directory does not exist, create one
mkdir -p "$REPORT_DIR"

REPORT_FILENAME="failure_report_$(date +"%Y%m%d_%H%M%S").xml"

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