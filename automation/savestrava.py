import re

# Load the HTML content
with open('example.html', 'r') as file:
    html_content = file.read()
    print("HTML Content Loaded:")
    print(html_content[:100])  # Print the first 500 characters

# Define the regex pattern
pattern = r"(https://www\.strava\.com/activities/)(\d{11})"

# Search for matches
matches = re.findall(pattern, html_content)

# Print the results
for match in matches:
    print(f"<a href='{match[0]}{match[1]}/export_original'>{match[1]}</a><br/>")
