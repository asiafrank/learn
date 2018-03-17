#include <boost/regex.hpp>
#include <iostream>
#include <string>
#include <QApplication>
#include <QPushButton>

int main(int argc, char *argv[]) {
    std::string line = "Subject: Re: Hello boy!!";
    boost::regex pat( "^Subject: (Re: |Aw: )*(.*)" );

    boost::smatch matches;
    if (boost::regex_match(line, matches, pat))
        std::cout << matches[2] << std::endl;

    QApplication a(argc, argv);
    QPushButton button("Hello!");
    button.show();
    return a.exec();
}