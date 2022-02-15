module.exports = {
  publicPath: "./",
  devServer: {
    https:true,
    port: 8083,
    open: true,
  },
  chainWebpack: config => {
    config
    .plugin('html')
    .tap(args => {
      args[0].title = '모두의 책갈피'
      return args
    })
    const svgRule = config.module.rule("svg");
    svgRule.uses.clear();
    svgRule.use("vue-svg-loader").loader("vue-svg-loader");
  },
  //outputDir: '../backend-java/src/main/resources/dist'
};
