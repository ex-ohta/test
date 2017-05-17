! #/bin/bash

export LANG=C.UTF-8

echo "********************"
echo "* install gems     *"
echo "********************"
sudo add-apt-repository -y ppa:brightbox/ruby-ng
sudo apt-get update
sudo apt-get -y install ruby2.1
sudo gem install checkstyle_filter-git saddler saddler-reporter-github findbugs_translate_checkstyle_format android_lint_translate_checkstyle_format pmd_translate_checkstyle_format
if [ $? -ne 0 ]; then
      echo 'Failed to install gems.'
      exit 1
fi
REPORTER=Saddler::Reporter::Github::CommitReviewComment
echo "********************"
echo "* checkstyle       *"
echo "********************"
cat ./app/build/reports/checkstyle/checkstyle.xml \
    | checkstyle_filter-git diff origin/master \
    | saddler report --require saddler/reporter/github --reporter $REPORTER
