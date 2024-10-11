import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'app-params',
    data: { pageTitle: 'lotteryApp.appParams.home.title' },
    loadChildren: () => import('./app-params/app-params.routes'),
  },
  {
    path: 'article-group',
    data: { pageTitle: 'lotteryApp.articleGroup.home.title' },
    loadChildren: () => import('./article-group/article-group.routes'),
  },
  {
    path: 'introduce-article-group',
    data: { pageTitle: 'lotteryApp.introduceArticleGroup.home.title' },
    loadChildren: () => import('./introduce-article-group/introduce-article-group.routes'),
  },
  {
    path: 'articles',
    data: { pageTitle: 'lotteryApp.articles.home.title' },
    loadChildren: () => import('./articles/articles.routes'),
  },
  {
    path: 'introduce-article',
    data: { pageTitle: 'lotteryApp.introduceArticle.home.title' },
    loadChildren: () => import('./introduce-article/introduce-article.routes'),
  },
  {
    path: 'prizes',
    data: { pageTitle: 'lotteryApp.prizes.home.title' },
    loadChildren: () => import('./prizes/prizes.routes'),
  },
  {
    path: 'results-every-day',
    data: { pageTitle: 'lotteryApp.resultsEveryDay.home.title' },
    loadChildren: () => import('./results-every-day/results-every-day.routes'),
  },
  {
    path: 'random-results',
    data: { pageTitle: 'lotteryApp.randomResults.home.title' },
    loadChildren: () => import('./random-results/random-results.routes'),
  },
  {
    path: 'deposits',
    data: { pageTitle: 'lotteryApp.deposits.home.title' },
    loadChildren: () => import('./deposits/deposits.routes'),
  },
  {
    path: 'file-saves',
    data: { pageTitle: 'lotteryApp.fileSaves.home.title' },
    loadChildren: () => import('./file-saves/file-saves.routes'),
  },
  {
    path: 'roles',
    data: { pageTitle: 'lotteryApp.roles.home.title' },
    loadChildren: () => import('./roles/roles.routes'),
  },
  {
    path: 'role-functions',
    data: { pageTitle: 'lotteryApp.roleFunctions.home.title' },
    loadChildren: () => import('./role-functions/role-functions.routes'),
  },
  {
    path: 'functions',
    data: { pageTitle: 'lotteryApp.functions.home.title' },
    loadChildren: () => import('./functions/functions.routes'),
  },
  {
    path: 'actions',
    data: { pageTitle: 'lotteryApp.actions.home.title' },
    loadChildren: () => import('./actions/actions.routes'),
  },
  {
    path: 'role-function-action',
    data: { pageTitle: 'lotteryApp.roleFunctionAction.home.title' },
    loadChildren: () => import('./role-function-action/role-function-action.routes'),
  },
  {
    path: 'level-deposits',
    data: { pageTitle: 'lotteryApp.levelDeposits.home.title' },
    loadChildren: () => import('./level-deposits/level-deposits.routes'),
  },
  {
    path: 'level-deposits-result',
    data: { pageTitle: 'lotteryApp.levelDepositsResult.home.title' },
    loadChildren: () => import('./level-deposits-result/level-deposits-result.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
