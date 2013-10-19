#!/usr/bin/env python3
"""
Convert http://www.citytimezones.info/ data from Windows timezones to proper
Unicode.org TZ names using the data from
http://unicode.org/repos/cldr/trunk/common/supplemental/windowsZones.xml

Usage:

./convert.py cities.csv windows_timezones.xml > cities_proper.xml
"""

import csv
import sys
from xml.etree import ElementTree


def main(cities_fp, windows_fp, out_fp):
    cities = csv.reader(cities_fp)
    windows = ElementTree.parse(windows_fp)
    out = csv.writer(out_fp)

    for row in cities:
        win_tz = row[5]
        uni_tz_match = windows.findall(
            './/mapZone[@territory="001"][@other="{}"]'.format(win_tz))

        if uni_tz_match:
            out.writerow([row[0], uni_tz_match[0].attrib['type']])
        else:
            sys.stderr.write("No timezone for {} found.\n".format(win_tz))


if __name__ == "__main__":
    cities_fp = open(sys.argv[1], 'r')
    windows_fp = open(sys.argv[2], 'r')

    main(cities_fp, windows_fp, sys.stdout)
