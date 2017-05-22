! #/bin/bash

export LANG=C.UTF-8

echo "********************"
echo "* install gems     *"
echo "********************"
sudo gem install checkstyle_filter-git saddler saddler-reporter-github findbugs_translate_checkstyle_format android_lint_translate_checkstyle_format pmd_translate_checkstyle_format
if [ $? -ne 0 ]; then
      echo 'Failed to install gems.'
      exit 1
fi
REPORTER=Saddler::Reporter::Github::PullRequestComment
echo "********************"
echo "* checkstyle       *"
echo "********************"
cat ./app/build/reports/checkstyle/checkstyle.xml \
    | /opt/python_tools/checkstyle_filter/checkstyle_filter_git_diff.py -b origin/master\
    | /opt/python_tools/github_report/reporter.py


echo "********************"
echo "* findbugs         *"
echo "********************"
cat ./app/build/reports/findbugs/findbugs_report.xml \
    | findbugs_translate_checkstyle_format translate \
    | /opt/python_tools/checkstyle_filter/checkstyle_filter_git_diff.py -b origin/master\
    | /opt/python_tools/github_report/reporter.py

echo "********************"
echo "* PMD              *"
echo "********************"
cat ./app/build/reports/pmd/pmd.xml \
    | pmd_translate_checkstyle_format translate \
    | /opt/python_tools/checkstyle_filter/checkstyle_filter_git_diff.py -b origin/master\
    | /opt/python_tools/github_report/reporter.py

echo "********************"
echo "* PMD-CPD          *"
echo "********************"
cat ./app/build/reports/pmd/cpd.xml \
    | pmd_translate_checkstyle_format translate --cpd-translate \
    | /opt/python_tools/checkstyle_filter/checkstyle_filter_git_diff.py -b origin/master\
    | /opt/python_tools/github_report/reporter.py

echo "********************"
echo "* android lint     *"
echo "********************"
cat ./app/build/reports/lint/lint_results.xml \
    | android_lint_translate_checkstyle_format translate \
    | /opt/python_tools/checkstyle_filter/checkstyle_filter_git_diff.py -b origin/master\
    | /opt/python_tools/github_report/reporter.py
