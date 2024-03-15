#!/bin/bash

# Define the output file for the report
REPORT_FILE="failure_report.xml"

# Start building the XML report
echo '<?xml version="1.0" encoding="UTF-8"?>' > "$REPORT_FILE"
echo '<failure_report>' >> "$REPORT_FILE"

# Example: Include build information
echo '  <build_info>' >> "$REPORT_FILE"
echo "    <build_number>${BUILD_NUMBER}</build_number>" >> "$REPORT_FILE"
echo "    <build_timestamp>$(date +%Y-%m-%d\ %H:%M:%S)</build_timestamp>" >> "$REPORT_FILE"
echo '  </build_info>' >> "$REPORT_FILE"

# Example: Include details of the failed stage
if [[ -n "$FAILED_STAGE_NAME" ]]; then
    echo '  <failed_stage>' >> "$REPORT_FILE"
    echo "    <stage_name>${FAILED_STAGE_NAME}</stage_name>" >> "$REPORT_FILE"
    echo "    <stage_details><![CDATA[${FAILED_STAGE_DETAILS}]]></stage_details>" >> "$REPORT_FILE"
    echo '  </failed_stage>' >> "$REPORT_FILE"
fi

# End the XML report
echo '</failure_report>' >> "$REPORT_FILE"

# Print the location of the generated report file for Jenkins
echo "Report generated: ${REPORT_FILE}"