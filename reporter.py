#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
wercker専用のレポーターになってる
"""

import os
import json
import pprint

import requests

__all__ = ['Reporter']

BASE_URL = "https://api.github.com"
TOKEN = os.environ['GITHUB_ACCESS_TOKEN']
ORG = os.environ['WERCKER_GIT_OWNER']
REPO = os.environ['WERCKER_GIT_REPOSITORY']
GET_PULLURL_TMP = "{base_url}/repos/{org}/{repo}/pulls"
CACHE_FILE = '/tmp/github_pull_info'
branch = os.environ['WERCKER_GIT_BRANCH']

class Reporter(object):
    def __init__(self):
        self._pull_info = None
        self._headers = {'Content-Type': 'application/json',
                         'Authorization': 'token ' + TOKEN}
        if os.path.isfile(CACHE_FILE):
            self._read_cache()
        else:
            self._get_pull_info()
            self._write_cache()

    def _generate_pulls_url(self):
        """
        Generate pull url
        HOST/repos/:owner/:repo/pulls
        """
        return GET_PULLURL_TMP.format(base_url=BASE_URL, org=ORG, repo=REPO)

    def _get_pull_info(self):
        """
        Get pull infor using pull API
        There is an assumption that one branch is used for one PR
        """
        url = self._generate_pulls_url()
        params = {'head': ORG + ":" + branch}
        r = requests.get(url, headers=self._headers, params=params)
        if r.status_code == 200:
            self._pull_info = json.loads(r.text)
            pprint.pprint(self._pull_info)

    def _generate_review_comment_url(self):
        """
        Generate review comment url
        HOST/repos/:owner/:repo/pulls/:num/comments
        """
        return self._pull_info[0]['review_comments_url']

    def _generate_comment_url(self):
        """
        Generate comment url
        HOST/repos/:owner/:repo/issues/:num/comments
        """
        return self._pull_info[0]['issue_url'] + '/comments'

    def _write_cache(self):
        with open(CACHE_FILE, 'w') as f:
            f.write(json.dumps(self._pull_info))
            f.flush()

    def _read_cache(self):
        l = None
        with open(CACHE_FILE, 'r') as f:
            l = f.readlines()[0]
        if l and self._pull_info == None:
            self._pull_info = json.loads(l)

    def review_comment(self, comment="test", path="reporter.py", position=79):
        """ Post review comment for this PR """
        url = self._generate_review_comment_url()
        commitId = os.environ['WERCKER_GIT_COMMIT']
        data = json.dumps({'body': comment, 'commit_id': commitId, 'path': path, 'position':position})
        r = requests.post(url, headers=self._headers, data=data)
        pprint.pprint(json.loads(r.text))

    def issue_comment(self, comment):
        """ Post issue comment for this PR """
        url = self._generate_comment_url()
        data = json.dumps({'body': comment})
        r = requests.post(url, headers=self._headers, data=data)
        pprint.pprint(json.loads(r.text))


if __name__ == "__main__":
    reporter = Reporter()
    comment = 'test'
    reporter.review_comment()
